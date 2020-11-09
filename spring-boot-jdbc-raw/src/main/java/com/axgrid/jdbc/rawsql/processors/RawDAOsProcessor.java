package com.axgrid.jdbc.rawsql.processors;


import com.axgrid.jdbc.rawsql.RawDAO;
import com.axgrid.jdbc.rawsql.RawObject;
import com.axgrid.jdbc.rawsql.RawUtils;
import com.axgrid.jdbc.rawsql.processors.dto.RawDAODescription;
import com.axgrid.jdbc.rawsql.processors.dto.RawDAOMethod;
import com.axgrid.jdbc.rawsql.processors.dto.RawDAOQueryMethod;
import com.axgrid.jdbc.rawsql.processors.dto.RawObjectDescription;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.axgrid.jdbc.rawsql.RawDAO")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("debug")
public class RawDAOsProcessor extends AbstractProcessor {

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
        System.out.println("--- PROCESS DAO !!!!!! ---");
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                try {
                    createDAO(element);
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Raw DAO creation exception: " + e.getMessage());
                    throw new RuntimeException("Raw DAO create exception", e);
                }
            }
        }
        return true;
    }

    private void createDAO(Element element) throws Exception {
        var rawDAO = element.getAnnotation(RawDAO.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "found @RawDAO at " + element);
        PackageElement packageElement = procEnv.getElementUtils().getPackageOf( element );
        Name name = element.getSimpleName();
        String mapperName = name.toString() + "RawDAO";

        RawDAODescription description = new RawDAODescription();
        description.setRawDAO(rawDAO);
        description.setObjectName(name.toString());
        description.setPackageName(packageElement.getQualifiedName().toString());

        for(var methodElement : element.getEnclosedElements()){
            var method = getMethodName(methodElement);
            switch (method) {
                case "update":
                case "insert":
                    description.getMethods().add(getMethodDescription(methodElement, method));
                    break;
                case "query":
                    description.getMethods().add(getQueryMethodDescription(methodElement, method));
                    break;
                default:
                    description.getMethods().add(getMethodDescription(methodElement, "undefined"));
                    break;
            }
        }

        JavaFileObject mapperFile = filer.createSourceFile(
                packageElement.getQualifiedName() + "." + mapperName );
        RawTemplate template = new RawTemplate();
        Writer writer = mapperFile.openWriter();
        writer.append(template.generate(description));
        writer.close();
    }

    private RawDAOMethod getMethodDescription(Element methodElement, String method) {
        ExecutableElement executableElement = (ExecutableElement) methodElement;
        var methodDescription = new RawDAOMethod();
        methodDescription.setQuery(getMethodAnnotationQuery(methodElement));
        methodDescription.setMethod(method);
        methodDescription.setName(methodElement.getSimpleName().toString());
        methodDescription.setReturnType(executableElement.getReturnType().toString());
        methodDescription.setParameters(RawDAOMethod.getParameters(executableElement));


        return methodDescription;
    }

    private RawDAOMethod getQueryMethodDescription(Element methodElement, String method) {
        ExecutableElement executableElement = (ExecutableElement) methodElement;
        var methodDescription = new RawDAOQueryMethod();
        var rawQuery = methodElement.getAnnotation(RawDAO.RawQuery.class);
        methodDescription.setQuery(getMethodAnnotationQuery(methodElement));
        methodDescription.setMethod(method);
        methodDescription.setName(methodElement.getSimpleName().toString());
        methodDescription.setReturnType(executableElement.getReturnType().toString());
        methodDescription.setNullIfObjectEmpty(rawQuery.nullIfObjectEmpty());
        methodDescription.setParameters(RawDAOMethod.getParameters(executableElement));
        List<? extends TypeMirror> mapperTypes = RawUtils.getTypeMirrorFromAnnotationValue(rawQuery::mapper);
        if (mapperTypes != null && mapperTypes.size() > 0)
                methodDescription.setMapperType(rawQuery.mapper()[0].getTypeName());
        return methodDescription;
    }


    private String getMethodName(Element methodElement) {
        if (methodElement.getAnnotation(RawDAO.RawUpdate.class) != null) return "update";
        if (methodElement.getAnnotation(RawDAO.RawQuery.class) != null) return "query";
        if (methodElement.getAnnotation(RawDAO.RawInsert.class) != null) return "insert";
        return "undefined";
    }

    private String getMethodAnnotationQuery(Element methodElement) {
        if (methodElement.getAnnotation(RawDAO.RawUpdate.class) != null) {
            var annotation = methodElement.getAnnotation(RawDAO.RawUpdate.class);
            return annotation.query() + annotation.value();
        }
        if (methodElement.getAnnotation(RawDAO.RawQuery.class) != null) {
            var annotation = methodElement.getAnnotation(RawDAO.RawQuery.class);
            return annotation.query() + annotation.value();
        }
        if (methodElement.getAnnotation(RawDAO.RawInsert.class) != null) {
            var annotation = methodElement.getAnnotation(RawDAO.RawInsert.class);
            return annotation.query() + annotation.value();
        }
        return "";
    }
}
