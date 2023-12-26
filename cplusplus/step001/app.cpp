import vector2;

int main(int ac, char* av[]) {

    Vector2 v1{1, 1};
    Vector2 v2{100, 100};

    auto v3 = (v1 + v2) * 0.5;

    std::cout << v3 << " = " << "0.5 * (" << v1 << " + " << v2 << ")"<< std::endl;

    v1 = projection(10.f, toRadians<float>(270.f));
    std::cout << "projection of 10, angle 270: " << v1 << std::endl;

    auto a1 = Vector2{10, 20};
    auto b1 = Vector2{50, 30};

    auto a2 = Vector2{20, 50};
    auto b2 = Vector2{30, 0};

    auto rc = isIntersect(a1, b1, a2, b2);

    std::cout << "Crossing (a1, b1) = (" << a1 << ", " << b1 <<   ")"
              << " and (a2, b2) = (" << a2 << ", " << b2 <<   "), result: " << rc << std::endl;


	return 0;
}
