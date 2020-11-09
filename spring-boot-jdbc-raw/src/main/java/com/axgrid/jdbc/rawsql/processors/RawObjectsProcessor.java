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
@SupportedSourceVersion(SourceVersion.RELEASE_8)
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

    private void createMapper(Element element) throws Exception {
        var rawObject = element.getAnnotation(RawObject.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "found @RawObject at " + element);
        PackageElement packageElement = procEnv.getElementUtils().getPackageOf( element );
        Name name = element.getSimpleName();
        String mapperName = name.toString() + "RawObjectMapper";

        RawObjectDescription description = new RawObjectDescription();
        description.setRawObject(rawObject);
        description.setObjectName(name.toString());
        description.setPackageName(packageElement.getQualifiedName().toString());

        for(var field : element.getEnclosedElements())
            createFields(field, description.getFields());

        JavaFileObject mapperFile = filer.createSourceFile(
                packageElement.getQualifiedName() + "." + mapperName );
        RawTemplate template = new RawTemplate();
        Writer writer = mapperFile.openWriter();
        writer.append(template.generate(description));
        writer.close();
    }

    private void createFields(Element element, RawObjectFieldList list) {
        String name = element.getSimpleName().toString();
        var include = element.getAnnotation(RawObject.Include.class);
        var exclude = element.getAnnotation(RawObject.Exclude.class);
        var jsonObject  = element.getAnnotation(RawObject.JsonObject.class);

        RawObjectField field;
        if (!element.getKind().isField()) { // Method
            ExecutableElement executableElement = (ExecutableElement) element;
            if (!RawUtils.isSetter(name) || executableElement.getTypeParameters().size() != 1) return; //Какой-то другой метод
            field = list.get(RawUtils.getSetterName(name));
            field.setType(executableElement.getTypeParameters().get(0).asType().toString());
            field.setSetter(true);
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
        if (jsonObject != null)
            field.setJsonObject(true);
    }
}
