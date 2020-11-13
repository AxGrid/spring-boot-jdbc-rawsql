{{~#if cache}}
    {{~#if cache.scheduled}}
        @org.springframework.scheduling.annotation.Scheduled({{cache.scheduledParams}})
    {{~/if}}
{{~/if~}}
