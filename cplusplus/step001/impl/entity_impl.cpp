module entity;

Entity::Entity(Component::id_type component_set) :
    component_set(component_set) {}

bool Entity::hasComponent(Component::id_type type) {
    return component_set & type;
};

void Entity::addComponent(Component::id_type type) {
    component_set |= type;
}
void Entity::removeComponent(Component::id_type type) {
    component_set &= ~type;
}

Component::Component(Component::id_type type, Entity* belongsTo) :
        type(type),
        entity(belongsTo) {
    belongsTo->addComponent(type);            
}

Rectangle::Rectangle(float width, float height) :
    Entity(),
    size(width, height)
    {}
