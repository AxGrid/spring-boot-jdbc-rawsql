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
import java.util.Set;

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
        System.out.println("--- PROCESS MAPPER !!!!!! ---");
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

        for(var field : element.getEnclosedElements()) //TODO: Order by field, setter, getter
            createFields(field, description.getFields());
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
        String name = element.getSimpleName().toString();
        var include = element.getAnnotation(RawObject.Include.class);
        var exclude = element.getAnnotation(RawObject.Exclude.class);
        var jsonObject  = element.getAnnotation(RawObject.JsonObject.class);
        var enumToInteger  = element.getAnnotation(RawObject.EnumToInteger.class);
        var processor  = element.getAnnotation(RawObject.Processor.class);

        RawObjectField field;
        if (!element.getKind().isField()) { // Method
            ExecutableElement executableElement = (ExecutableElement) element;
            if (RawUtils.isGetter(name) && executableElement.getTypeParameters().size() != 0) {
                field = list.get(RawUtils.getGetterName(name));
                //var elementType = executableElement getTypeParameters().get(0);
                //field.setType(elementType.asType().toString());
                field.setSetter(true);
            }
            else if (RawUtils.isSetter(name) && executableElement.getTypeParameters().size() == 1) {
                field = list.get(RawUtils.getSetterName(name));
                var elementType = executableElement.getTypeParameters().get(0);
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

        if (enumToInteger != null) {
            field.setValueProcessor("enumToInt");
        }

        if (element.getAnnotation(RawObject.EnumToOrdinal.class) != null) {
            field.setValueProcessor("enumToOrdinal");
        }

        if (processor != null) {
            field.setValueProcessor(processor.value() + processor.name());
        }



    }


}
