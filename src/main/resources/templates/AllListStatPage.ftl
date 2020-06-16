<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">All list of elements:</h3>
    <div>
        <@ml.myList EntireListElem>
        </@ml.myList>
    </div>
</body>
</@c.page>