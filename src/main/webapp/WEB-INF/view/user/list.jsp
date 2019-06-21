<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>TimeTracking - Manage Users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">TimeTracker</a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav mr-auto">
                <a class="nav-item nav-link" href="/activity/user/list">My Activities <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="/activity/list">All Activities</a>
                <a class="nav-item nav-link active" href="/user/list">Manage Users</a>
            </div>
            <div class="nav navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Logout</a>
                </li>
            </div>
        </div>
    </nav>

    <br/><h2 class="text-center">Manage Users</h2><br/>

    <div class="container">

        <div class="text-right">
            <a type="button" class="btn btn-primary" href="/user/add">Add User</a>
        </div>
        <br />
        <table class="table table-striped table-bordered">
            <tr>
                <th class="text-center">ID</th>
                <th class="text-center">Name</th>
                <th class="text-center">Email</th>
                <th class="text-center">Role</th>
                <th class="text-center">Action</th>
            </tr>

            <c:forEach items="${users}" var="user">
                <c:url var="updateLink" value="/user/update">
                    <c:param name="userId" value="${user.id}" />
                </c:url>

                <c:url var="deleteLink" value="/user/delete">
                    <c:param name="userId" value="${user.id}" />
                </c:url>

                <tr>
                    <td class="text-center">${user.getId()}</td>
                    <td class="text-center">${user.getName()}</td>
                    <td class="text-center">${user.getEmail()}</td>
                    <td class="text-center">${user.getRole()}</td>
                    <td class="text-center">
                        <a type="button" class="btn btn-secondary btn-sm" href="${updateLink}">Update</a>
                        <a type="button" class="btn btn-danger btn-sm" href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <nav aria-label="Navigation for activities">
            <ul class="pagination justify-content-end">
                <c:if test="${currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link" href="/user/list?currentPage=${currentPage-1}">Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${pages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link"> ${i}
                                    <span class="sr-only">(current)</span>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="/user/list?currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt pages}">
                    <li class="page-item">
                        <a class="page-link" href="/user/list?currentPage=${currentPage+1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>

    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
    </script>

</body>

</html>