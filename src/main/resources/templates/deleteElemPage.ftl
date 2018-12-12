<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Delete an element:</h3>
    <form method="post" action="wallAftDelOne">
        <div class="addNewInputs">
            <h4>Number:</h4><input type="number" name="number" class="editAndDelInp" required>
            <#if numberError??>
                <div class="invalid-feedback">
                    ${numberError}
                </div>
            </#if>
            <#if errorNumberExists??>
                <div class="invalid-feedback">
                    ${errorNumberExists}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Delete</button>
    </form>
    <form method="get" action="delOneOrSeveral" class="delRadioForm">
            <h4 >One element:<input type="radio" value="one" name="radioDel" checked id="delRadio"></h4>
            <h4>Several elements:<input type="radio" value="several" name="radioDel"></h4>
        <button class="delButton">Change</button>
    </form>
</body>
</@c.page>