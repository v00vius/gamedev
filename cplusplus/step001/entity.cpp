export module entity;
import component;

export class Entity {
private:
    ComponentType components;

public:
    bool hasComponent(ComponentType type) {
        return components & type;
    };

    void addComponent(ComponentType type) {
        components |= type;
    }
    void removeComponent(ComponentType type) {
        components &= ~type;
    }
};
