export module component;
export import <cstdint>;

class Entity;

export enum class ComponentType: std::uint_fast16_t {
    position =  0b00000001,
    speed =     0b00000010,
    color =     0b00000100
};

export bool operator & (const ComponentType a, const ComponentType b) {
    return a & b;
}
export void operator |= (ComponentType& a, const ComponentType b) {
    a |= b;
}
export void operator &= (ComponentType& a, const ComponentType b) {
    a &= b;
}
export ComponentType operator ~ (ComponentType a) {
    return ~a;
}

export class Component {
private:
    const ComponentType type;
    Entity* const belongsTo;

public:
    Component(ComponentType type, Entity* belongsTo) :
        type(type),
        belongsTo(belongsTo) {

    }

    virtual ~Component() = default;
    virtual void apply() const = 0;
};
