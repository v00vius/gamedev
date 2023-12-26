export module entity;
export import <cstdint>;
import vector2;

class Entity;
export class Component {
public:
    using id_type = std::uint_fast16_t;

    enum id : id_type  {
        position =  0b00000001,
        speed =     0b00000010,
        color =     0b00000100,
        size =     0b00001000
    };

    Component(Component::id_type type, Entity* belongsTo);

    virtual ~Component() = default;
    virtual void apply() const {};

private:
    const id_type type;
    Entity* const entity;
};

export class Entity {
private:
    Component::id_type component_set;

public:
    Entity(Component::id_type component_set = 0);

    bool hasComponent(Component::id_type type) {
        return component_set & type;
    };

    void addComponent(Component::id_type type) {
        component_set |= type;
    }
    void removeComponent(Component::id_type type) {
        component_set &= ~type;
    }
};

export class Rectangle : public Entity {
public:
    Rectangle(float width, float height);

private:
    Vector2 size;
};

export class Size : public Component {
public:
    
private:
    float* size;    
};
