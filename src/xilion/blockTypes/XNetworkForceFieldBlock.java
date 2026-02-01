package xilion.blockTypes;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * NetworkForceFieldBlock
 * - Shared ShieldNetwork per connected group of blocks
 * - Convex hull -> expanded polygon net
 * - ForceProjector-style bullet interception using Groups.bullet.intersect(...)
 * - setBars() override for shared shield bar
 */
public class XNetworkForceFieldBlock extends Block {

    public float linkRange = 180f;           // search radius for other nodes
    public float padding = 24f;              // how far outside nodes the net should go
    public float baseShieldHealth = 350f;    // baseline multiplier for shield HP
    public float regen = 2.5f;               // shield regen per second
    public Color fieldColor = Color.cyan.cpy();

    // --- static consumer state (ForceProjector pattern) ---
    protected static XNetworkForceFieldBlock paramBlock;
    protected static XNetworkForceFieldBuild paramEntity;
    protected static final Cons<Bullet> shieldConsumer = bullet -> {
        if(paramEntity == null || paramBlock == null) return;

        // quick checks then precise polygon test
        if(bullet.team != paramEntity.team && bullet.type.absorbable && !bullet.absorbed
                && paramEntity.network != null && paramEntity.network.polyVerts.length >= 6
                && paramEntity.pointInPolygon(bullet.x, bullet.y, paramEntity.network.polyVerts)) {

            // consume bullet
            bullet.absorb();

            // effect + drain shield by bullet's shieldDamage
            Fx.shieldApply.at(bullet.x, bullet.y, paramBlock.fieldColor);
            float dmg = bullet.type.shieldDamage(bullet);
            paramEntity.network.shieldHealth -= dmg;
            paramEntity.network.hit = 1f;

            if(paramEntity.network.shieldHealth <= 0f){
                Fx.shieldBreak.at(bullet.x, bullet.y, paramBlock.fieldColor);
            }
        }
    };

    public XNetworkForceFieldBlock(String name){
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.projectors;
        hasPower = false; // enable if you want power consumption
    }

    // Shared network container
    static class ShieldNetwork{
        Seq<XNetworkForceFieldBuild> members = new Seq<>();
        float shieldHealth = 0f;
        float maxShieldHealth = 0f;
        float[] polyVerts = new float[0];
        float polyArea = 0f;
        float hit = 0f; // transient indicator for UI blink
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("shield", entity -> new Bar(
                () -> Core.bundle.get("bar.shield"),
                () -> fieldColor,
                () -> {
                    XNetworkForceFieldBuild b = (XNetworkForceFieldBuild)entity;
                    ShieldNetwork net = b.network;
                    return net == null || net.maxShieldHealth <= 0f ? 0f : net.shieldHealth / net.maxShieldHealth;
                }
        ));
    }

    public class XNetworkForceFieldBuild extends Building {
        // per-build pointer to the shared network
        ShieldNetwork network = null;

        // convenience: local list of discovered nearby builds (used during rebuild)
        Seq<XNetworkForceFieldBuild> found = new Seq<>();

        @Override
        public void created(){
            rebuildNetwork();
        }

        @Override
        public void updateTile(){
            // rebuild network sometimes (every 30 ticks)
            if(timer.get(0, 30)){
                rebuildNetwork();
            }

            // shared regen
            if(network != null){
                network.shieldHealth = Mathf.approach(network.shieldHealth, network.maxShieldHealth, regen * Time.delta);
                network.hit = Mathf.approach(network.hit, 0f, Time.delta * 3f);
            }

            // intercept bullets every tick while shield active
            if(network != null && network.shieldHealth > 0f && network.polyVerts.length >= 6){
                deflectBullets();
            }
        }

        /** ForceProjector-style bounding-box bullet scan using static consumer. */
        void deflectBullets(){
            // set static params
            paramBlock = XNetworkForceFieldBlock.this;
            paramEntity = this;

            // bounding box of polygon
            float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY;
            float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
            int n = network.polyVerts.length / 2;
            for(int i=0;i<n;i++){
                float vx = network.polyVerts[i*2];
                float vy = network.polyVerts[i*2+1];
                if(vx < minX) minX = vx;
                if(vy < minY) minY = vy;
                if(vx > maxX) maxX = vx;
                if(vy > maxY) maxY = vy;
            }

            if(minX == Float.POSITIVE_INFINITY) {
                paramBlock = null;
                paramEntity = null;
                return;
            }

            float w = Math.max(1f, maxX - minX);
            float h = Math.max(1f, maxY - minY);

            Groups.bullet.intersect(minX, minY, w, h, shieldConsumer);

            // clear statics
            paramBlock = null;
            paramEntity = null;
        }

        @Override
        public boolean absorbLasers(){
            return network != null && network.shieldHealth > 0f && network.polyVerts.length >= 6;
        }

        @Override
        public void damage(float amount){
            if(network != null && network.shieldHealth > 0f){
                network.shieldHealth -= amount;
                network.hit = 1f;
                Fx.shieldApply.at(x, y, fieldColor);
                if(network.shieldHealth <= 0f) Fx.shieldBreak.at(x, y, fieldColor);
                return;
            }
            super.damage(amount);
        }

        @Override
        public boolean collision(Bullet bullet){
            if(network == null || network.shieldHealth <= 0f || network.polyVerts.length < 6) return false;

            if(pointInPolygon(bullet.x, bullet.y, network.polyVerts)){
                network.shieldHealth -= bullet.damage;
                bullet.absorb();
                network.hit = 1f;
                Fx.shieldApply.at(bullet.x, bullet.y, fieldColor);
                if(network.shieldHealth <= 0f) Fx.shieldBreak.at(bullet.x, bullet.y, fieldColor);
                return true;
            }
            return false;
        }

        @Override
        public void draw(){
            super.draw();

            if(network == null || network.shieldHealth <= 0f || network.polyVerts.length < 6) return;

            Draw.z(Layer.shields);
            float alpha = Mathf.clamp(network.shieldHealth / network.maxShieldHealth);
            Draw.color(fieldColor, alpha);

            // filled polygon (try Fill.poly, fallback to triangle fan)
            try{
                Fill.poly(FloatSeq.with(network.polyVerts));
            }catch(Throwable t){
                Point centroid = computeCentroidFromFloatVerts(network.polyVerts);
                int m = network.polyVerts.length / 2;
                for(int i=0;i<m;i++){
                    int j = (i+1) % m;
                    Fill.tri(centroid.x, centroid.y,
                            network.polyVerts[i*2], network.polyVerts[i*2+1],
                            network.polyVerts[j*2], network.polyVerts[j*2+1]);
                }
            }

            // outline
            Lines.stroke(1.6f);
            int m = network.polyVerts.length / 2;
            for(int i=0;i<m;i++){
                int j = (i+1) % m;
                Lines.line(network.polyVerts[i*2], network.polyVerts[i*2+1],
                        network.polyVerts[j*2], network.polyVerts[j*2+1]);
            }

            Draw.reset();
        }

        /** Rebuild (or create / merge) a shared ShieldNetwork for this cluster of nodes. */
        void rebuildNetwork(){
            // find nearby same-type builds
            found.clear();
            Groups.build.each(b -> {
                if(b != null && b.block == block && b.within(this, linkRange)){
                    found.add((XNetworkForceFieldBuild)b);
                }
            });

            if(!found.contains(this, true)) found.add(this);

            // find first existing network among found
            ShieldNetwork shared = null;
            for(XNetworkForceFieldBuild b : found){
                if(b.network != null){
                    shared = b.network;
                    break;
                }
            }

            // collect shieldHealth to transfer from other networks (if merging)
            float transferredShield = 0f;
            if(shared == null) shared = new ShieldNetwork();

            for(XNetworkForceFieldBuild b : found){
                if(b.network != null && b.network != shared){
                    transferredShield += b.network.shieldHealth;
                    // clear old membership list to avoid stale references
                    b.network.members.clear();
                }
            }

            // assign the shared network to all found members
            shared.members.clear();
            for(XNetworkForceFieldBuild b : found){
                if(!shared.members.contains(b, true)) shared.members.add(b);
                b.network = shared;
            }

            // geometry: compute convex hull of member positions
            int n = shared.members.size;
            if(n == 0){
                shared.polyVerts = new float[0];
                shared.polyArea = 0f;
                shared.maxShieldHealth = baseShieldHealth;
                shared.shieldHealth = Mathf.clamp(shared.shieldHealth + transferredShield, 0f, shared.maxShieldHealth);
                return;
            }

            Point[] pts = new Point[n];
            for(int i=0;i<n;i++){
                XNetworkForceFieldBuild b = shared.members.get(i);
                pts[i] = new Point(b.x, b.y);
            }

            Point[] hull = convexHull(pts);

            // centroid and expansion
            Point centroid = computeCentroid(hull);
            float[] expanded = new float[hull.length * 2];
            for(int i=0;i<hull.length;i++){
                float vx = hull[i].x - centroid.x;
                float vy = hull[i].y - centroid.y;
                float len = (float)Math.hypot(vx, vy);
                if(len <= 0.0001f){
                    expanded[i*2] = hull[i].x;
                    expanded[i*2+1] = hull[i].y;
                }else{
                    float nx = vx / len;
                    float ny = vy / len;
                    expanded[i*2]   = hull[i].x + nx * padding;
                    expanded[i*2+1] = hull[i].y + ny * padding;
                }
            }

            shared.polyVerts = expanded;
            shared.polyArea = Math.abs(polygonArea(expanded));

            float areaFactor = Math.max(0.1f, shared.polyArea / 3000f);
            shared.maxShieldHealth = baseShieldHealth * Math.max(1, shared.members.size) * areaFactor;

            // transfer previous shield HP into new network and clamp
            shared.shieldHealth = Mathf.clamp(shared.shieldHealth + transferredShield, 0f, shared.maxShieldHealth);
        }

        // ---------------------------
        // geometry helpers
        // ---------------------------

        // monotone chain convex hull
        Point[] convexHull(Point[] pts){
            if(pts == null || pts.length == 0) return new Point[0];
            Point[] arr = Arrays.copyOf(pts, pts.length);
            Arrays.sort(arr, (a,b) -> {
                if(a.x == b.x) return Float.compare(a.y, b.y);
                return Float.compare(a.x, b.x);
            });

            ArrayList<Point> lower = new ArrayList<>();
            for(Point p : arr){
                while(lower.size() >= 2 && cross(lower.get(lower.size()-2), lower.get(lower.size()-1), p) <= 0){
                    lower.remove(lower.size()-1);
                }
                lower.add(p);
            }

            ArrayList<Point> upper = new ArrayList<>();
            for(int i = arr.length - 1; i >= 0; i--){
                Point p = arr[i];
                while(upper.size() >= 2 && cross(upper.get(upper.size()-2), upper.get(upper.size()-1), p) <= 0){
                    upper.remove(upper.size()-1);
                }
                upper.add(p);
            }

            ArrayList<Point> hull = new ArrayList<>();
            for(int i=0;i<lower.size();i++) hull.add(lower.get(i));
            for(int i=1;i<upper.size()-1;i++) hull.add(upper.get(i));
            if(hull.size() == 0 && arr.length > 0) hull.add(arr[0]);

            return hull.toArray(new Point[hull.size()]);
        }

        float cross(Point a, Point b, Point c){
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        }

        Point computeCentroid(Point[] poly){
            if(poly == null || poly.length == 0) return new Point(x, y);
            float cx = 0f, cy = 0f;
            for(Point p : poly){ cx += p.x; cy += p.y; }
            return new Point(cx / poly.length, cy / poly.length);
        }

        Point computeCentroidFromFloatVerts(float[] verts){
            float cx = 0f, cy = 0f;
            int n = verts.length / 2;
            for(int i=0;i<n;i++){
                cx += verts[i*2];
                cy += verts[i*2+1];
            }
            return new Point(cx / n, cy / n);
        }

        float polygonArea(float[] verts){
            int n = verts.length / 2;
            if(n < 3) return 0f;
            float area = 0f;
            for(int i=0;i<n;i++){
                int j = (i+1)%n;
                area += verts[i*2] * verts[j*2+1] - verts[j*2] * verts[i*2+1];
            }
            return area * 0.5f;
        }

        boolean pointInPolygon(float px, float py, float[] verts){
            int n = verts.length / 2;
            boolean inside = false;
            for(int i=0, j=n-1; i<n; j=i++){
                float xi = verts[i*2], yi = verts[i*2+1];
                float xj = verts[j*2], yj = verts[j*2+1];
                boolean intersect = ((yi > py) != (yj > py)) &&
                        (px < (xj - xi) * (py - yi) / (yj - yi + 0.0000001f) + xi);
                if(intersect) inside = !inside;
            }
            return inside;
        }
    }

    static class Point {
        float x, y;
        Point(float x, float y){ this.x = x; this.y = y; }
    }
}
