export module entity;

export import <cstdint>;
export import <string>;

import vector2;

class Entity;

export
class Component {
public:
    using id_type = std::uint_fast16_t;

    enum id : id_type  {
        position =  0b00000001,
        speed =     0b00000010,
        color =     0b00000100,
        size =      0b00001000
    };

    Component(Component::id_type type, const std::string& name, Entity* belongsTo);

    virtual ~Component() = default;
    virtual void apply() const {};

private:
    const std::string name;
    const id_type type;
    Entity* entity;
};

export
class Entity {

public:
    Entity(const std::string& name, Component::id_type component_set = 0);

    bool hasComponent(Component::id_type type) {
        return component_set & type;
    };

    void addComponent(Component::id_type type) {
        component_set |= type;
    }
    void removeComponent(Component::id_type type) {
        component_set &= ~type;
    }
private:
    const std::string name;
    Component::id_type component_set;
};

export
class Size : public Component {
public:
    Size(const std::string& name, Entity* entity, float* what);

private:
    float* size;    
};

export class Position : public Component {
public:
    Position(const std::string& name, Entity* entity, Vector2* what);

private:
    Vector2* position;    
};

export
class Speed : public Component {
public:
    Speed(const std::string& name, Entity* entity, Vector2* position, Vector2* velocity);

private:
    Vector2* position;
    Vector2* velosity;
};

export
class Rectangle : public Entity {
public:
    Rectangle(const std::string& name, float width, float height);

private:
    Vector2 size;
};

export
class Circle : public Entity {
public:
    Circle(const std::string& name, float radius);

private:
    float radius;
};
