<#import "parts/common.ftl" as c>
<#import  "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">List of repeating:</h3>
    <@ml.myList listRep>
    </@ml.myList>
    <br><br><br>
    <form method="get" action="pickElemRepeat">
        <button class="addNewButton">Repeat</button>
    </form>
</body>
</@c.page>