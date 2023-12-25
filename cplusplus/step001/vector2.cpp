
export module vector2;

export import <iostream>;
export import <numbers>;
export import <cmath>;

export class Vector2
{
public:
    float x;
    float y;

    Vector2() : x{}, y{} {}
    Vector2(Vector2& a) : x{a.x}, y{a.y} {}
    Vector2(float x, float y) : x{x}, y{y} {}

    Vector2 operator + (Vector2 b) {
        return {x + b.x, y + b.y};
    }
    Vector2 operator - (Vector2 b) {
        return {x - b.x, y - b.y};
    }
    Vector2 operator * (float scale) {
        return {scale * x, scale * y};
    }
    Vector2& operator += (Vector2 b) {
        x += b.x;
        y += b.y;

        return *this;
    }
    Vector2& operator -= (Vector2 b) {
        x -= b.x;
        y -= b.y;

        return *this;
    }

    Vector2& projection(float length, float degrees) {
        float rad = (degrees * std::numbers::pi_v<float>) / 180.0;

        x = length * cos(rad);
        y = length * sin(rad);

        return *this;
    }
    float length() {
        return sqrt(x * x + y * y);
    }
    float angle() {
        if(abs(x) < 1e-6)
            return 90.f;

        return 180.f * atan2(y, x) / std::numbers::pi_v<float>;
    }
    Vector2 rotateRelative(float deltaDegrees) {
        return projection(length(), deltaDegrees + angle());
    }
    float distance(Vector2 b) {
        auto v = *this - b;

        return v.length();
    }
    friend
    Vector2 abs(Vector2 a) {
        return {fabs(a.x), fabs(a.y)};
    }
    bool isInsideOf(Vector2 a, Vector2 b) {
        if(x < a.x || x > b.x)
            return false;

        if(y < a.y || y > b.y)
            return false;

        return true;
    }
    friend
    bool isIntersect(Vector2 a1, Vector2 b1,
                Vector2 a2, Vector2 b2) {
        //  1
        b1 -= a1;
        a2 -= a1;
        b2 -= a1;
        a1 -= a1;

        //  2
        float angle = b1.angle();

        b1.rotateRelative(-angle);
        a2.rotateRelative(-angle);
        b2.rotateRelative(-angle);

        //  3
        if(abs(a2.y - b2.y) < 1e-5)
            return false;

        auto c = abs(b2 - a2);

        float x = c.x < 1e-5 ? a2.x : a2.y * c.x / c.y;

        return -1e-5f <= x && x <= (b1.x + 1e-5f);
    }

    friend
    std::ostream& operator << (std::ostream& os, const Vector2 a) {
        os << "{" << a.x << ", " << a.y << "}";

        return os;
    }
};

