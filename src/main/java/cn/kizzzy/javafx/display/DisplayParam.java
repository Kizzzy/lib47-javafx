package cn.kizzzy.javafx.display;

import java.awt.image.BufferedImage;

public class DisplayParam {
    private int layer;
    private float x;
    private float y;
    private float z;
    private float width;
    private float height;
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private boolean flipX;
    private boolean flipY;
    private BufferedImage image;
    
    public int getLayer() {
        return layer;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getRotateX() {
        return rotateX;
    }
    
    public float getRotateY() {
        return rotateY;
    }
    
    public float getRotateZ() {
        return rotateZ;
    }
    
    public boolean isFlipX() {
        return flipX;
    }
    
    public boolean isFlipY() {
        return flipY;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public static class Builder {
        private int layer;
        private float x;
        private float y;
        private float z;
        private float width;
        private float height;
        private float rotateX;
        private float rotateY;
        private float rotateZ;
        private boolean flipX;
        private boolean flipY;
        private BufferedImage image;
        
        public int getLayer() {
            return layer;
        }
        
        public Builder setLayer(int layer) {
            this.layer = layer;
            return this;
        }
        
        public float getX() {
            return x;
        }
        
        public Builder setX(float x) {
            this.x = x;
            return this;
        }
        
        public float getY() {
            return y;
        }
        
        public Builder setY(float y) {
            this.y = y;
            return this;
        }
        
        public float getZ() {
            return z;
        }
        
        public Builder setZ(float z) {
            this.z = z;
            return this;
        }
        
        public float getWidth() {
            return width;
        }
        
        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }
        
        public float getHeight() {
            return height;
        }
        
        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }
        
        public float getRotateX() {
            return rotateX;
        }
        
        public Builder setRotateX(float rotateX) {
            this.rotateX = rotateX;
            return this;
        }
        
        public float getRotateY() {
            return rotateY;
        }
        
        public Builder setRotateY(float rotateY) {
            this.rotateY = rotateY;
            return this;
        }
        
        public float getRotateZ() {
            return rotateZ;
        }
        
        public Builder setRotateZ(float rotateZ) {
            this.rotateZ = rotateZ;
            return this;
        }
        
        public boolean isFlipX() {
            return flipX;
        }
        
        public Builder setFlipX(boolean flipX) {
            this.flipX = flipX;
            return this;
        }
        
        public boolean isFlipY() {
            return flipY;
        }
        
        public Builder setFlipY(boolean flipY) {
            this.flipY = flipY;
            return this;
        }
        
        public BufferedImage getImage() {
            return image;
        }
        
        public Builder setImage(BufferedImage image) {
            this.image = image;
            return this;
        }
        
        public DisplayParam build() {
            DisplayParam param = new DisplayParam();
            param.layer = layer;
            param.x = x;
            param.y = y;
            param.z = z;
            param.width = width;
            param.height = height;
            param.rotateX = rotateX;
            param.rotateY = rotateY;
            param.rotateZ = rotateZ;
            param.flipX = flipX;
            param.flipY = flipY;
            param.image = image;
            return param;
        }
    }
}
