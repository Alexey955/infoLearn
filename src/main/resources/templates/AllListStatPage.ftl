<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">All list of elements:</h3>
    <@ml.myList EntireListElem>
    </@ml.myList>
</body>
</@c.page>