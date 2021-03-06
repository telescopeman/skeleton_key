/**
 * Something that's bound to the existence of another object.
 */

public abstract class UpperBucketCrab {
     protected final boolean linked;
     protected abstract void onPulledDown();
     protected final LowerBucketCrab myCrab;

     public UpperBucketCrab(LowerBucketCrab linked_crab)
     {
          link_to_crab(linked_crab);
          myCrab = linked_crab;
          linked = true;
     }

     public UpperBucketCrab()
     {
          linked = false;
          myCrab = null;
     }

     protected void link_to_crab(LowerBucketCrab linked_crab)
     {
          BucketCrabManager.addCrabPair(linked_crab,this);
     }
}
