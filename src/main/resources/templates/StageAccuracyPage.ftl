<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Stage accuracy:</h3>
    <div>
    <#list GroupByStage as x>
        <div>There are ${x.count} elements in the ${x.stage} stage, average accuracy is ${x.avg}%.</div>
    </#list>
    </div>
</body>
</@c.page>