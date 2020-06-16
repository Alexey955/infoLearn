<#import "parts/common.ftl" as c>
<#import  "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">Elements in stages:</h3>

    <#if ElemInStage??>
        <@ml.myList ElemInStage>
        </@ml.myList>
    <#else>
        <h3 class="AVGAccuracyText">There are not elements.</h3>
    </#if>

</body>
</@c.page>