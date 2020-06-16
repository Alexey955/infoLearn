<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Users list:</h3>
    <#list userList as userList>
        <div>
            id: ${userList.id},
            username: ${userList.username},
            <a target="_top" href="/pickOperatUser/${userList.id}">pick</a>
        </div>
    </#list>
</body>
</@c.page>