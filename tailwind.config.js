/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./frontend/src/**/*.cljs"],
  theme: {
    extend: {},
    fontFamily: {
      'spooky': ['Jolly Lodger'],
      'sans': ['Helvetica', 'Arial', 'sans-serif'],
    }
  },
  plugins: [],
}
