<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">List of elements for ${pickedDay}:</h3>
    <@ml.myList DayListWithoutTail>
    </@ml.myList>

    <h4 align="center">With a tail:</h4>

    <@ml.myList DayListWithTail>
    </@ml.myList>
</body>
</@c.page>
