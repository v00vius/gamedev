module entity;

Entity::Entity(const std::string& name, Component::id_type component_set) :
    name(name),
    component_set(component_set) {
}

bool Entity::hasComponent(Component::id_type type) {
    return component_set & type;
};

void Entity::addComponent(Component::id_type type) {
    component_set |= type;
}

void Entity::removeComponent(Component::id_type type) {
    component_set &= ~type;
}

Component::Component(Component::id_type type, const std::string& name, Entity* belongsTo) :
        type(type),
        name(name),
        entity(belongsTo) {
    entity->addComponent(type);            
}

Size::Size(const std::string& name, Entity* entity, float* what) :
    Component(Component::id::size, name, entity),
    size(what) {
}

Position::Position(const std::string& name, Entity* entity, Vector2* what) :
    Component(Component::id::position, name, entity),
    position(what) {
}

Rectangle::Rectangle(const std::string& name, float width, float height) :
    Entity(name),
    size(width, height) {
}

Circle::Circle(const std::string& name, float radius) :
    Entity(name),
    radius(radius) {
}
