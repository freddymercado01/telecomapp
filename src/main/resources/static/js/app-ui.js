(function () {
  function normalize(value) {
    return (value || "")
      .toString()
      .toLowerCase()
      .normalize("NFD")
      .replace(/[\u0300-\u036f]/g, "");
  }

  function setupTableFilters() {
    const filters = document.querySelectorAll("[data-table-search]");

    filters.forEach((input) => {
      const tableId = input.getAttribute("data-table-search");
      const table = document.getElementById(tableId);
      if (!table) return;

      const rows = Array.from(table.querySelectorAll("tbody tr[data-filter-row]"));
      const noResultsRow = table.querySelector("tbody tr.no-results-row");

      input.addEventListener("input", () => {
        const term = normalize(input.value.trim());
        let visibleCount = 0;

        rows.forEach((row) => {
          const text = normalize(row.textContent);
          const show = term === "" || text.includes(term);
          row.style.display = show ? "" : "none";
          if (show) visibleCount += 1;
        });

        if (noResultsRow) {
          noResultsRow.classList.toggle("visible", visibleCount === 0);
        }
      });
    });
  }

  function setupDeleteConfirm() {
    const forms = document.querySelectorAll("form[data-confirm]");
    forms.forEach((form) => {
      form.addEventListener("submit", (event) => {
        const message = form.getAttribute("data-confirm") || "¿Confirmas esta acción?";
        if (!window.confirm(message)) {
          event.preventDefault();
        }
      });
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    setupTableFilters();
    setupDeleteConfirm();
  });
})();
