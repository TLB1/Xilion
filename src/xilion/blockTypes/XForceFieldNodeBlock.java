package xilion.blockTypes;


import mindustry.world.Block;

public class XForceFieldNodeBlock extends Block {
    public XForceFieldNodeBlock(String name) {
        super(name);
    }

    /*
    private ArrayList<UPForceFieldNodeBlock> connectedBlocks;

    public UPForceFieldNodeBlock(String name) {
        super(name);
        connectedBlocks = new ArrayList<>();
    }

    public void addConnectedBlock(UPForceFieldNodeBlock block) {
        connectedBlocks.add(block);
    }

    @Override
    public void unitOn(Unit unit) {
        // Check if the unit is an enemy
        if (unit.team != Team.sharded) {
            // Check if the unit is within the force field polygon
            if (isWithinForceFieldPolygon(unit)) {
                // Shield the unit from damage
                unit.hit = unit.health();
            }
        }
    }


    private boolean isWithinForceFieldPolygon(Unit unit) {
        // Get the center position of the unit
        float unitX = unit.x, unitY = unit.y;

        // Calculate the total number of connected blocks
        int numConnectedBlocks = connectedBlocks.size();

        // Create arrays to store the x and y coordinates of the polygon vertices
        float[] verticesX = new float[numConnectedBlocks];
        float[] verticesY = new float[numConnectedBlocks];

        // Fill the vertex coordinate arrays with the connected blocks' positions
        for (int i = 0; i < numConnectedBlocks; i++) {
            UPForceFieldNodeBlock block = connectedBlocks.get(i);
            verticesX[i] = block.x;
            verticesY[i] = block.y;
        }
    }
        // Check if the unit is within the polygon
        private boolean isWithinForceFieldPolygon(Unit unit) {
            // Get the center position of the unit
            float unitX = unit.x, unitY = unit.y;

            // Calculate the total number of connected blocks
            int numConnectedBlocks = connectedBlocks.size();

            // Create arrays to store the x and y coordinates of the polygon vertices
            float[] verticesX = new float[numConnectedBlocks];
            float[] verticesY = new float[numConnectedBlocks];

            // Fill the vertex coordinate arrays with the connected blocks' positions
            for (int i = 0; i < numConnectedBlocks; i++) {
                UPForceFieldNodeBlock block = connectedBlocks.get(i);
                verticesX[i] = block.x;
                verticesY[i] = block.y;
            }

            // Check if the unit is within the polygon
            return isPointInPolygon(unitX, unitY, verticesX, verticesY);
        }

        private boolean isPointInPolygon(float x, float y, float[] verticesXValues, float[] verticesYValues) {
            int intersectCount = 0;
            int vertexCount = verticesXValues.length;

            for (int i = 0; i < vertexCount; i++) {
                float x1 = verticesXValues[i];
                float y1 = verticesYValues[i];
                float x2 = verticesXValues[(i + 1) % vertexCount];
                float y2 = verticesYValues[(i + 1) % vertexCount];

                if (intersect(x1, y1, x2, y2, x, y)) {
                    intersectCount++;
                }
            }

            return intersectCount % 2 == 1;
        }

        private boolean intersect(float x1, float y1, float x2, float y2, float x, float y) {
            return (y1 > y) != (y2 > y) && (x < (x2 - x1) * (y - y1) / (y2 - y1) + x1);
        }
*/
}
