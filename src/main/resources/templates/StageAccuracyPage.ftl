<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Stage accuracy:</h3>
    <div>
    <#list GroupByStage as x>
    <div>There are <span>${x.count}</span> elements in the <span>${x.stage}</span> stage, average amount of mistakes is ${x.avg}%.</div>
        <#else>
            <h3 class="AVGAccuracyText">There are not elements.</h3>
    </#list>
    </div>
</body>
</@c.page>