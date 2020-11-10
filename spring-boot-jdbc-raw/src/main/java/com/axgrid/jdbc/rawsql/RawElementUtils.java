package com.axgrid.jdbc.rawsql;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import com.google.auto.common.MoreElements;

public final class RawElementUtils {

    public static TypeElement getFromMirrorType(Types typeUtils, TypeMirror type) {
        if (type.getKind() == TypeKind.DECLARED) {
            Element returnTypeElement = typeUtils.asElement(type);
            if (returnTypeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
                return MoreElements.asType(returnTypeElement);
            }
        }
        throw new RuntimeException("Can't take Mirror type for " + type.toString());
    }

}
