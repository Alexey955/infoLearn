<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <#if avgAccuracy??>
        <h3 class="AVGAccuracyText">Average amount of mistakes is <span>${avgAccuracy}</span>%.</h3>
        <#else>
            <h3 class="AVGAccuracyText">There are not elements.</h3>
    </#if>
</body>
</@c.page>
