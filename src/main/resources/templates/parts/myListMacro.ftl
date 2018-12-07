<#macro myList modelName>
    <#list modelName as x>
        <div>
            ${x.number} number,
            ${x.stage} stage,
            ${x.percentFalse}% of mistakes,
            ${x.datePriorRep} prior date of repeating.<br>
        </div>
    </#list>
</#macro>