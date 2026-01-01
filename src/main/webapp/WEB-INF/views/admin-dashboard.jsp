<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - XYZ Gym</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --accent: #ff6f00;
            --bg-dark: #121212;
            --header-bg: #181818;
            --sidebar-bg: #1a1a1a;
            --card-bg: #1f1f1f;
            --text-main: #e0e0e0;
        }
        body { font-family: 'Poppins', sans-serif; background-color: var(--bg-dark); color: var(--text-main); margin: 0; }
        .header { display:flex; justify-content:space-between; align-items:center; padding:14px 30px; background-color: var(--header-bg); border-bottom:1px solid #2a2a2a; position: fixed; width:100%; z-index:1001; }
        .brand { font-size:1.2rem; font-weight:600; color: var(--accent); }
        .btn-logout { background: linear-gradient(135deg,#ff6f00,#ff3d00); border:none; color:#fff; border-radius:20px; padding:6px 16px; }
        .sidebar { position:fixed; top:0; left:-250px; width:250px; height:100%; background-color:var(--sidebar-bg); padding-top:70px; transition:0.3s; z-index:1000; }
        .sidebar.active { left:0; }
        .sidebar a { display:block; padding:14px 25px; color:#cfcfcf; text-decoration:none; font-size:0.95rem; }
        .sidebar a:hover { background-color:#2a2a2a; color:#fff; }
        .sidebar a i { margin-right:10px; color: var(--accent); }
        .content { padding:90px 30px 30px 30px; transition: margin-left 0.3s; }
        .content.shifted { margin-left:250px; }
        .card { background-color: var(--card-bg); border-radius:12px; border:none; margin-bottom:20px; color:#e5e5e5; }
        .table thead { background-color:#222; color:#fff; }
        .table tbody tr:hover { background-color:#2a2a2a; }
        select, input { background-color: #2a2a2a; color: #e0e0e0; border: 1px solid #444; border-radius: 5px; }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <div class="d-flex align-items-center gap-3">
        <span class="menu-btn" onclick="toggleSidebar()"><i class="fas fa-bars"></i></span>
        <span class="brand">XYZ Gym Admin</span>
    </div>
    <div>
        <a href="/logout" class="btn btn-logout btn-sm"><i class="fas fa-sign-out-alt"></i> Logout</a>
    </div>
</div>

<!-- Sidebar -->
<div id="sidebar" class="sidebar">
    <a href="#members"><i class="fas fa-users"></i> Members</a>
    <a href="#plans"><i class="fas fa-calendar-alt"></i> Plans</a>
    <a href="#assign"><i class="fas fa-dumbbell"></i> Assign Plan</a>
</div>

<!-- Content -->
<div id="content" class="content">

    <!-- Members Section -->
    <section id="members" class="card p-3">
        <h4>Members List</h4>
        <table class="table table-hover text-light">
            <thead>
                <tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Plan</th><th>Expiry</th></tr>
            </thead>
            <tbody>
                <c:forEach items="${members}" var="member">
                    <tr>
                        <td>${member.id}</td>
                        <td>${member.name}</td>
                        <td>${member.email}</td>
                        <td>${member.phone}</td>
                        <td><c:choose><c:when test="${not empty member.plan}">${member.plan.planName}</c:when><c:otherwise>None</c:otherwise></c:choose></td>
                        <td>${member.expiryDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>

    <!-- Plans Section -->
    <section id="plans" class="card p-3">
        <h4>Plans List</h4>
        <!-- Button to open Add Plan Modal -->
        <button class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#addPlanModal">
            <i class="fas fa-plus"></i> Add Plan
        </button>
        <table class="table table-hover text-light">
            <thead>
                <tr><th>ID</th><th>Name</th><th>Duration</th><th>Price</th></tr>
            </thead>
            <tbody>
                <c:forEach items="${plans}" var="plan">
                    <tr>
                        <td>${plan.id}</td>
                        <td>${plan.planName}</td>
                        <td>${plan.durationMonths}</td>
                        <td>${plan.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>

    <!-- Assign Plan Section -->
    <section id="assign" class="card p-3">
        <h4>Assign Plan to Member</h4>
        <!-- Button to open modal -->
        <button class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#assignPlanModal">
            <i class="fas fa-dumbbell"></i> Assign Plan
        </button>
    </section>
</div>

<!-- ================= Add Plan Modal ================= -->
<div class="modal fade" id="addPlanModal" tabindex="-1" aria-labelledby="addPlanModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-light">
            <div class="modal-header">
                <h5 class="modal-title" id="addPlanModalLabel">Add New Plan</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form id="addPlanForm">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="planName" class="form-label">Plan Name</label>
                        <input type="text" id="planName" name="planName" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="durationMonths" class="form-label">Duration (Months)</label>
                        <input type="number" id="durationMonths" name="durationMonths" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" id="price" name="price" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add Plan</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- ================= Assign Plan Modal ================= -->
<div class="modal fade" id="assignPlanModal" tabindex="-1" aria-labelledby="assignPlanModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-light">
            <div class="modal-header">
                <h5 class="modal-title" id="assignPlanModalLabel">Assign Plan to Member</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form id="assignPlanForm">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="memberSelect" class="form-label">Member</label>
                        <select id="memberSelect" name="memberId" class="form-select bg-secondary text-light" required>
                            <c:forEach items="${members}" var="member">
                                <option value="${member.id}">${member.name} (${member.email})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="planSelect" class="form-label">Plan</label>
                        <select id="planSelect" name="planId" class="form-select bg-secondary text-light" required>
                            <c:forEach items="${plans}" var="plan">
                                <option value="${plan.id}">${plan.planName} (${plan.durationMonths} months)</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Assign Plan</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Sidebar toggle -->
<script>
function toggleSidebar() {
    document.getElementById('sidebar').classList.toggle('active');
    document.getElementById('content').classList.toggle('shifted');
}
</script>

<!-- AJAX for Add Plan -->
<script>
document.getElementById('addPlanForm').addEventListener('submit', function(e){
    e.preventDefault();
    let formData = new FormData(this);
    fetch('/admin/addPlan', {
        method: 'POST',
        body: new URLSearchParams(formData)
    }).then(res => res.text())
      .then(() => { alert('Plan added successfully!'); location.reload(); })
      .catch(err => console.error(err));
});
</script>

<!-- AJAX for Assign Plan -->
<script>
document.getElementById('assignPlanForm').addEventListener('submit', function(e){
    e.preventDefault();
    let formData = new FormData(this);
    fetch('/admin/assignPlan', {
        method: 'POST',
        body: new URLSearchParams(formData)
    }).then(res => res.text())
      .then(() => { alert('Plan assigned successfully!'); location.reload(); })
      .catch(err => console.error(err));
});
</script>

</body>
</html>
