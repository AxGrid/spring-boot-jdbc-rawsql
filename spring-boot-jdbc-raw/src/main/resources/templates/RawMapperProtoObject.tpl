try{
        byte[] byte__{{fieldName}} = resultSet.getBytes("{{fieldName}}");
    {{#if setter~}}
        res.{{methodName}}(byte__{{fieldName}} == null ? null : {{objectType}}.parseFrom(byte__{{fieldName}}){{valueProcessorArguments.[1]}});
    {{else}}
        res.{{name}} = byte__{{fieldName}} == null ? null : {{objectType}}.parseFrom(byte__{{fieldName}}){{valueProcessorArguments.[1]}};
    {{~/if}}
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
    {{#if setter~}}
        res.{{methodName}}({{default}});
    {{else~}}
        res.{{name}} = {{default}};
    {{/if~}}
    }
