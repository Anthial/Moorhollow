import hy
from min import app


if __name__ == "__main__":
    app.run(host="192.168.0.33", port=5000, debug=True)