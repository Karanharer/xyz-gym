<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Member Dashboard - XYZ Gym</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Google Font (Inter) -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/member-dashboard.css">
</head>

<body>

<!-- ================= SIDEBAR ================= -->
<aside id="sidebar" class="sidebar">
    <div class="sidebar-logo-container">
        <img src="../images/logo.png"  alt="XYZ Gym Logo" class="sidebar-logo">
    </div>

    <a href="#dashboard" class="nav-link active">
        <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a href="#profile" class="nav-link">
        <i class="fas fa-user"></i> Profile
    </a>
    <a href="#plan" class="nav-link">
        <i class="fas fa-calendar-alt"></i> My Plan
    </a>
    <a href="#payments" class="nav-link">
        <i class="fas fa-credit-card"></i> Payments
    </a>
    <a href="#attendance" class="nav-link">
        <i class="fas fa-check-circle"></i> Attendance
    </a>
    <a href="#notifications" class="nav-link">
        <i class="fas fa-bell"></i> Notifications
    </a>

    <a href="/logout" class="btn btn-logout">
        <i class="fas fa-sign-out-alt"></i> Logout
    </a>
</aside>

<!-- ================= HEADER ================= -->
<header class="header">
    <span class="toggle-btn" onclick="toggleSidebar()">
        <i class="fas fa-bars"></i>
    </span>
    <span class="brand">Welcome, ${member.name}</span>
</header>

<!-- ================= CONTENT ================= -->
<main id="content" class="content">

    <!-- ===== DASHBOARD CARDS ===== -->
    <section id="dashboard" class="mb-4">
        <div class="row g-3">
            <!-- Active Plan -->
            <div class="col-md-3 col-sm-6">
                <div class="card dashboard-card text-center">
                    <h5>Active Plan</h5>
                    <h3>
                        <c:choose>
                            <c:when test="${not empty member.plan}">
                                ${member.plan.planName}
                            </c:when>
                            <c:otherwise>None</c:otherwise>
                        </c:choose>
                    </h3>
                    <c:if test="${empty member.plan}">
                        <button class="btn btn-primary btn-sm mt-2" onclick="openPlanModal()">Select Plan</button>
                    </c:if>
                </div>
            </div>

            <!-- Total Payments -->
            <div class="col-md-3 col-sm-6">
                <div class="card dashboard-card text-center">
                    <h5>Total Payments</h5>
                    <h3>₹ ${paymentsTotal}</h3>
                </div>
            </div>

            <!-- Attendance -->
            <div class="col-md-3 col-sm-6">
                <div class="card dashboard-card text-center">
                    <h5>Attendance</h5>
                    <h3>${attendancePercent}%</h3>
                </div>
            </div>

            <!-- Plan Expiry -->
            <div class="col-md-3 col-sm-6">
                <div class="card dashboard-card text-center">
                    <h5>Plan Expiry</h5>
                    <c:choose>
                        <c:when test="${not empty member.plan}">
                            <div class="progress mt-2">
                                <div class="progress-bar" style="width:${planProgress}%">
                                    ${planProgress}%
                                </div>
                            </div>
                            <small>Expires on ${member.expiryDate}</small>
                        </c:when>
                        <c:otherwise>
                            <span class="text-danger">No Plan</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>

    <!-- ===== PROFILE ===== -->
    <section id="profile" class="card section-card mb-4 p-4">
        <h4 class="mb-3">Profile</h4>
        <hr>
        <p><b>Name:</b> ${member.name}</p>
        <p><b>Email:</b> ${member.email}</p>
        <p><b>Phone:</b> ${member.phone}</p>
        <a href="/member/updateProfile" class="btn btn-primary btn-sm mt-2">Update Profile</a>
    </section>

    <!-- ===== NOTIFICATIONS ===== -->
    <section id="notifications" class="card section-card mb-4 p-4">
        <h4 class="mb-3">Notifications</h4>
        <hr>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">🏋️ New batch starts from Monday</li>
            <li class="list-group-item">🎉 Renewal discount available</li>
            <li class="list-group-item">📅 Gym closed on public holidays</li>
        </ul>
    </section>

</main>

<!-- ================= PLAN MODAL ================= -->
<div id="planModal" class="plan-modal">
    <div class="plan-modal-content">
        <h4>Select a Plan</h4>
        <hr>

        <form action="${pageContext.request.contextPath}/member/selectPlan" method="post">
            <c:forEach var="plan" items="${plansList}">
                <div class="plan-card">
                    <div>
                        <h6>${plan.planName}</h6>
                        <small>${plan.durationMonths} Months</small><br>
                        <strong>₹ ${plan.price}</strong>
                    </div>
                    <input type="radio" name="planId" value="${plan.id}" required>
                </div>
            </c:forEach>

            <button class="btn btn-primary w-100 mt-3">Proceed to Payment</button>
        </form>

        <button class="btn btn-secondary w-100 mt-2" onclick="closePlanModal()">Cancel</button>
    </div>
</div>

<!-- ================= JS ================= -->
<script>
    function toggleSidebar() {
        document.getElementById("sidebar").classList.toggle("active");
        document.getElementById("content").classList.toggle("shifted");
    }

    function openPlanModal() {
        document.getElementById("planModal").classList.add("active");
    }

    function closePlanModal() {
        document.getElementById("planModal").classList.remove("active");
    }

    // Auto open plan modal if user has no plan
    <c:if test="${empty member.plan}">
        window.onload = openPlanModal;
    </c:if>
</script>

</body>
</html>
