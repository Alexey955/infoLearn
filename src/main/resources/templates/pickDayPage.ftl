<#import "parts/common.ftl" as c>

<@c.page>
<body>
<h3 align="center">Select a day:</h3>
<form method="get" action="showDayList">
    <div class="addNewInputs">
        <h4>Day:</h4><input type="text" name="dayField" class="editAndDelInp" required = ""/>
        <#if dateSelectError??><div class="invalid-feedback">${dateSelectError}</div></#if>
        <#--<#if dateSelectError??>
            <div class="invalid-feedback">
            ${dateSelectError}
            </div>
        </#if>-->
    </div>
    <button class="addNewButton">Pick</button>
</form>
</body>
</@c.page>