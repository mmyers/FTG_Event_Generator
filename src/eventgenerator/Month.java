// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

public enum Month
{
    january(30), 
    february(28), 
    march(30), 
    april(30), 
    may(30), 
    june(30), 
    july(30), 
    august(30), 
    september(30), 
    october(30), 
    november(30), 
    december(30), 
    none(-1);
    
    private int numDays;
    
    private Month(final int numDays) {
        this.numDays = numDays;
    }
    
    public final int getNumDays() {
        return this.numDays;
    }
}
