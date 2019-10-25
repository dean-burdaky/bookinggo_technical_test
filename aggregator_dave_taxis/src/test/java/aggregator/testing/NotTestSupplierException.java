package aggregator.testing;

import aggregator.data.Supplier;

class NotTestSupplierException extends RuntimeException
{

    NotTestSupplierException(Supplier supplier)
    {
        super("Supplier " + supplier.name() + " is not one of the test suppliers.");
    }
}
