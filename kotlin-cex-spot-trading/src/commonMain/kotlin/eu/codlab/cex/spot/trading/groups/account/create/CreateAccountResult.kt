package eu.codlab.cex.spot.trading.groups.account.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResult(
    /**
     * By default indicates name of new sub-account, which has been created. Nevertheless,
     * if sub-account name requested by Client already exists, CEX.IO Spot Trading will return the
     * name of this sub-account without creating of new sub-account with the same name.
     */
    val accountId: String
)
