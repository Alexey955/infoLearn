<#macro myList modelName>
    <#list modelName as x>
<div xmlns="http://www.w3.org/1999/html">
            <span>${x.number} number,</span>
            <span>${x.stage} stage,</span>
            ${x.percentFalse}% of mistakes,
            ${x.datePriorRep} prior date of repeating.<br/>
        </div>
    </#list>
</#macro>