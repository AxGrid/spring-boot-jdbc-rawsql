package com.axgrid.jdbc.rawsql.processors;

import com.axgrid.jdbc.rawsql.RawObject;
import com.axgrid.jdbc.rawsql.RawUtils;
import com.axgrid.jdbc.rawsql.processors.dto.RawObjectDescription;
import com.axgrid.jdbc.rawsql.processors.dto.RawObjectField;
import com.axgrid.jdbc.rawsql.processors.dto.RawObjectFieldList;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SupportedAnnotationTypes("com.axgrid.jdbc.rawsql.RawObject")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedOptions("debug")
public class RawObjectsProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private ProcessingEnvironment procEnv;


    @Override
    public synchronized void init(ProcessingEnvironment pe) {
        super.init(pe);
        this.filer = pe.getFiler();
        this.messager = pe.getMessager();
        this.procEnv = pe;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                try {
                    createMapper(element);
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Raw mapper creation exception: " + e.getMessage());
                    throw new RuntimeException("Raw mapper create exception", e);
                }
            }
        }
        return true;
    }

    public static RawObjectDescription getRawObjectDescription(Element element) {
        RawObjectDescription description = new RawObjectDescription();
        List<Future<String>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newWorkStealingPool();
        for(var field : element.getEnclosedElements()) {
            futures.add(executorService.submit(() -> {
                createFields(field, description.getFields());
                return field.getSimpleName().toString();
            }));
        }

        for(var future : futures) {
            try {
                future.get();
            }catch (InterruptedException | ExecutionException ignore) {
                break;
            }
        }
        return description;
    }

    private void createMapper(Element element) throws Exception {
        var rawObject = element.getAnnotation(RawObject.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "found @RawObject at " + element);
        PackageElement packageElement = procEnv.getElementUtils().getPackageOf( element );
        Name name = element.getSimpleName();
        String mapperName = name.toString() + "RawObjectMapper";

        RawObjectDescription description = getRawObjectDescription(element);
        description.setRawObject(rawObject);
        description.setObjectName(name.toString());
        description.setPackageName(packageElement.getQualifiedName().toString());

        JavaFileObject mapperFile = filer.createSourceFile(
                packageElement.getQualifiedName() + "." + mapperName );
        RawTemplate template = new RawTemplate();
        Writer writer = mapperFile.openWriter();
        writer.append(template.generate(description));
        writer.close();
    }

    private static void createFields(Element element, RawObjectFieldList list) {
        if (element.getKind().isClass()) return;
        String name = element.getSimpleName().toString();
        var include = element.getAnnotation(RawObject.Include.class);
        var exclude = element.getAnnotation(RawObject.Exclude.class);
        var jsonObject  = element.getAnnotation(RawObject.JsonObject.class);
        var enumToInteger  = element.getAnnotation(RawObject.EnumToInteger.class);
        var processor  = element.getAnnotation(RawObject.Processor.class);



        RawObjectField field;
        if (!element.getKind().isField()) { // Method
            ExecutableElement executableElement = (ExecutableElement) element;
            if (RawUtils.isGetter(name) && executableElement.getTypeParameters().size() == 0) {
                field = list.get(RawUtils.getGetterName(name));
                var elementType = executableElement.getReturnType();
                field.setType(elementType.toString());
                field.setGetter(true);
            }
            else if (RawUtils.isSetter(name) && executableElement.getParameters().size() == 1) {
                field = list.get(RawUtils.getSetterName(name));
                var elementType = executableElement.getParameters().get(0);
                field.setType(elementType.asType().toString());
                field.setSetter(true);
            } else {
                return;
            }
        } else {
            field = list.get(name);
            field.setField(true);
            field.setType(element.asType().toString());

        }

        if (exclude != null) field.setExclude(true);
        if (include != null) {
            field.setInclude(true);
            field.setFieldName(include.value() + include.fieldName());
        }
        if (jsonObject != null) {
            field.setJsonObject(true);
            field.setValueProcessor("json");
        }

        var protoObject = element.getAnnotation(RawObject.ProtoObject.class);
        if (protoObject != null) {
            field.setValueProcessor("proto");
            field.setBuilder(RawUtils.isBuilder(field.getType()));
            if (field.isBuilder() || protoObject.builder()) {
                field.getValueProcessorArguments().add(".build()");
                field.getValueProcessorArguments().add(".toBuilder()");
            }else {
                field.getValueProcessorArguments().add("");
                field.getValueProcessorArguments().add("");
            }
        }

        if (enumToInteger != null) {
            field.setValueProcessor("enumToInt");
            field.getValueProcessorArguments().add(enumToInteger.getter());
            field.getValueProcessorArguments().add(enumToInteger.setter());
        }

        var enumToString = element.getAnnotation(RawObject.EnumToString.class);
        if (enumToString != null) {
            field.setValueProcessor("enumToString");
            field.getValueProcessorArguments().add(enumToString.getter());
            field.getValueProcessorArguments().add(enumToString.setter());
        }

        if (element.getAnnotation(RawObject.EnumToOrdinal.class) != null) {
            field.setValueProcessor("enumToOrdinal");
        }

        if (element.getAnnotation(RawObject.DateToLong.class) != null) {
            field.setValueProcessor("dateToLong");
        }

        var dateToString = element.getAnnotation(RawObject.DateToString.class);
        if (dateToString != null) {
            field.setValueProcessor("dateToString");
            field.getValueProcessorArguments().add(dateToString.format());
        }

        if (processor != null) {
            field.setValueProcessor(processor.value() + processor.name());
            field.getValueProcessorArguments().addAll(Arrays.asList(processor.params()));
        }
    }


}
