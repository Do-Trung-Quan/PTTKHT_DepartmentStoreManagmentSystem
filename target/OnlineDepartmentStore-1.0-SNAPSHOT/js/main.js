/* === 1. Hiển thị thông báo từ server === */
function showServerMessage() {
    const messageBox = document.getElementById("messageBox");
    if (messageBox && messageBox.textContent.trim() !== "") {
        alert(messageBox.textContent.trim());
    }
}

/* === 2. Kiểm tra email & số điện thoại trong form đăng ký === */
function initRegisterFormValidation() {
    const form = document.getElementById("registerForm");
    if (!form) return;
    
    const emailInput = document.getElementById("email");
    const phoneInput = document.getElementById("telephone");
    const emailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
    const phoneRegex = /^(0[0-9]{9})$/; // Ví dụ: 10 chữ số, bắt đầu bằng 0

    function validateEmail() {
        if (emailRegex.test(emailInput.value.trim())) {
            emailInput.classList.remove("invalid");
            return true;
        } else {
            emailInput.classList.add("invalid");
            return false;
        }
    }

    function validatePhone() {
        const value = phoneInput.value.trim();
        if (value === "" || phoneRegex.test(value)) {
            phoneInput.classList.remove("invalid");
            return true;
        } else {
            phoneInput.classList.add("invalid");
            return false;
        }
    }
    
    emailInput.addEventListener("input", validateEmail);
    phoneInput.addEventListener("input", validatePhone);

    form.addEventListener("submit", (e) => {
        const emailOk = validateEmail();
        const phoneOk = validatePhone();
        if (!emailOk || !phoneOk) {
            e.preventDefault();
            alert("⚠️ Vui lòng nhập đúng cú pháp Email hoặc Số điện thoại!");
        }
    });
}

function initAddFormValidation() {
    const form = document.getElementById("addForm");
    if (!form) return;

    const priceInput = document.getElementById("price");

    function validatePrice() {
        if (isNaN(priceInput.value.trim()) || parseFloat(priceInput.value.trim()) < 0 || parseFloat(priceInput.value.trim()) > 1000000000) {
            priceInput.classList.add("invalid");
            return false;
        } else {
            priceInput.classList.remove("invalid");
            return true;
        }
    }

    priceInput.addEventListener("input", validatePrice);

    form.addEventListener("submit", (e) => {
        const priceOk = validatePrice();
        if (!priceOk) {
            e.preventDefault();
            alert("⚠️ Vui lòng nhập đúng cú pháp của giá!");
        }
    });
}

/* === 3. Kiểm tra dữ liệu nhập trong bảng Selected Item === */
function validateAndSubmit(form) {
    const price = parseFloat(form.importUnitPrice.value);
    const qty = parseInt(form.quantity.value);

    if (isNaN(price) || isNaN(qty)) {
        alert("⚠️ Giá trị nhập không hợp lệ!");
        return false;
    }
    if (price < 0 || qty < 1) {
        alert("⚠️ Giá phải không âm & số lượng phải lớn hơn 0!");
        return false;
    }
    if (price > 1000000000) {
        alert("⚠️ Giá không được vượt quá 1.000.000.000!");
        return false;
    }
    if (qty > 1000) {
        alert("⚠️ Số lượng không được vượt quá 1000!");
        return false;
    }

    form.submit();
}

/* === 4. Xử lý chọn hàng trong bảng Selected Item + nút Remove === */
function initSelectableTable() {
    const rows = document.querySelectorAll(".selectable-row");
    const removeBtn = document.getElementById("btnRemove");
    const removeId = document.getElementById("removeId");
    if (!rows.length || !removeBtn || !removeId) return;

    let selectedRow = null;
    rows.forEach(row => {
        row.addEventListener("click", () => {
            if (selectedRow) selectedRow.classList.remove("selected-row");
            selectedRow = row;
            selectedRow.classList.add("selected-row");
            removeId.value = row.dataset.id;
            removeBtn.disabled = false;
        });
    });

    // Khi click ra ngoài bảng → bỏ chọn hàng
    document.addEventListener("click", (event) => {
        // Kiểm tra xem click có phải vào button Remove không
        if (removeBtn.contains(event.target)) return;
        
        const clickedInsideTable = event.target.closest(".selectable-row");
        if (!clickedInsideTable && selectedRow) {
            selectedRow.classList.remove("selected-row");
            selectedRow = null;
            removeId.value = "";
            removeBtn.disabled = true;
        }
    });
}

// Hàm submit cho các hàng clickable trong bảng
function submitSelectForm(id) {
    document.getElementById("form-select-" + id).submit();
}

// Lưu vị trí cuộn trước khi reload
function saveScrollPos() {
    localStorage.setItem("scrollPos", window.scrollY);
}

/* === 5. Tự động giữ vị trí cuộn sau khi load lại trang === */
function saveScrollPosWhenReload() {
    // Khôi phục vị trí cuộn khi trang nạp lại
    window.addEventListener("load", function () {
        const pos = localStorage.getItem("scrollPos");
        if (pos) {
            window.scrollTo(0, parseInt(pos));
            localStorage.removeItem("scrollPos");
        }
    });
}

/* === 6. Tự động khởi chạy các hàm cần thiết khi load === */
document.addEventListener("DOMContentLoaded", () => {
    showServerMessage();
    initRegisterFormValidation();
    initAddFormValidation();
    initSelectableTable();
    saveScrollPosWhenReload();
});
