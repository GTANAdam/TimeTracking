<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>TimeTracking - Updating Activity</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">TimeTracker</a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav mr-auto">
                <a class="nav-item nav-link" href="/activity/user/list">My Activities</a>
                <a class="nav-item nav-link active" href="/activity/list">All Activities <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="/user/list">Manage Users</a>
            </div>
            <div class="nav navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Logout</a>
                </li>
            </div>
        </div>
    </nav>

    <br />
    <h2 class="text-center">Update Activity</h2><br />

    <div class="container">
        <form name="update" action="${pageContext.request.contextPath}/activity/update" method="post">

            <input type="text" name="activityId" value="${activity.id}" hidden>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                    <input type="text" name="name" class="form-control" placeholder="Activity description/name" value="${activity.name}" required>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Start Date</label>
                <div class="col-sm-10">
                    <input type="date" name="startDate" class="form-control" value="${activity.startDate}">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">End Date</label>
                <div class="col-sm-10">
                    <input type="date" name="endDate" class="form-control" value="${activity.endDate}">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Status</label>
                <div class="col-sm-10">
                    <select name="status" class="form-control">
                        <option value="Approved" selected>Approved</option>
                        <option value="Awaiting Approval">Awaiting Approval</option>
                        <option value="Awaiting Deletion">Awaiting Deletion</option>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">User</label>
                <div class="col-sm-10">
                    <select name="userId" class="form-control">
                        <%--@elvariable id="users" type="java.util.List"--%>
                        <c:forEach var="tempUser" items="${users}">
                            <c:choose>
                                <c:when test="${activity.user.id == tempUser.id}">
                                    <option value="${tempUser.id}" selected>${tempUser.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${tempUser.id}">${tempUser.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm text-right">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </form>

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