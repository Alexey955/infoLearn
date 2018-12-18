<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Delete several elements:</h3>
    <form method="post" action="wallAftDelSeveral">
        <div>
            <div id="inpFrom">
                <h4>Number from:</h4><input type="number" name="numberFieldFrom" required = "" class="inpDelWidth"/><br/>
            </div>
            <div id="inpInto">
                <h4>Number to:</h4><input type="number" name="numberFieldTo" required = "" class="inpDelWidth"/>
                <#if errorFromOrTo??><div class="invalid-feedback">${errorFromOrTo}</div></#if>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="addNewButton">Delete</button>
    </form>
    <form method="get" action="delOneOrSeveral" class="delRadioForm">
        <h4>One element:<input type="radio" value="one" name="radioDel"/></h4>
        <h4>Several elements:<input type="radio" value="several" name="radioDel" checked = ""/></h4>
        <button class="delButton">Change</button>
    </form>
</body>
</@c.page>