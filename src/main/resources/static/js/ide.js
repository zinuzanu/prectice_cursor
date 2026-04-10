(() => {
  const root = document.querySelector(".ide-root");
  if (!root) return;

  const THEME_KEY = "board.theme";

  function applyTheme(theme) {
    root.setAttribute("data-theme", theme);
  }

  function getPreferredTheme() {
    const saved = localStorage.getItem(THEME_KEY);
    if (saved === "light" || saved === "dark") return saved;
    return "dark";
  }

  applyTheme(getPreferredTheme());

  document.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-action='toggle-theme']");
    if (!btn) return;
    const next = root.getAttribute("data-theme") === "dark" ? "light" : "dark";
    localStorage.setItem(THEME_KEY, next);
    applyTheme(next);
  });

  function autosize(textarea) {
    textarea.style.height = "auto";
    textarea.style.height = `${textarea.scrollHeight}px`;
  }

  function attachAutosize() {
    document.querySelectorAll("textarea").forEach((ta) => {
      autosize(ta);
      ta.addEventListener("input", () => autosize(ta));
    });
  }

  function attachCharCount() {
    document.querySelectorAll("[data-maxlength]").forEach((el) => {
      const max = parseInt(el.getAttribute("data-maxlength"), 10);
      if (!Number.isFinite(max)) return;

      const counter = document.createElement("div");
      counter.className = "form-text text-muted mt-1";
      el.insertAdjacentElement("afterend", counter);

      const update = () => {
        const len = (el.value || "").length;
        counter.textContent = `${len.toLocaleString()} / ${max.toLocaleString()}`;
      };
      update();
      el.addEventListener("input", update);
    });
  }

  attachAutosize();
  attachCharCount();
})();

