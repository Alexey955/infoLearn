<#import "parts/common.ftl" as c>
<#import  "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">Elements in stages:</h3>
    <@ml.myList ElemInStage>
    </@ml.myList>
</body>
</@c.page>