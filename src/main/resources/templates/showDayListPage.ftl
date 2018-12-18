<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">List of elements for <#if pickedDay??>${pickedDay}<#else>null</#if>:</h3>
        <@ml.myList DayListWithoutTail>
        </@ml.myList>


    <h4 align="center">With a tail:</h4>
    <span>
        <@ml.myList DayListWithTail>
        </@ml.myList>
    </span>


</body>
</@c.page>
