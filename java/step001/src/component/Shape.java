package component;

import types.Context;
import types.Drawable;
import types.Node;
import types.Vector2;

public abstract class Shape extends Node implements Drawable {
    private Vector2 position;
    private Vector2 velocity;

    public Shape() {
        position = new Vector2();
        velocity = new Vector2();
    }

    @Override
    public void draw(Context drawContext) {
        DrawContext ctx = (DrawContext) drawContext;
//        Vector2 p = new Vector2(ctx.screenPosition).add(position);

//        ctx.drawList.addRectFilled(p.x, p.y, p.x + 1, p.y + 1, ctx.color);
        ctx.drawList.addRectFilled(ctx.screenPosition.x + position.x,
                                    ctx.screenPosition.y + position.y,
                                    ctx.screenPosition.x + position.x + 1,
                                    ctx.screenPosition.y + position.y + 1,
                                    ctx.color
        );
    }
}
