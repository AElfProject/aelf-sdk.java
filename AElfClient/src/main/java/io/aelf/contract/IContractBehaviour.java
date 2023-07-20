package io.aelf.contract;

import com.google.gson.JsonObject;
import io.aelf.schemas.TransactionDto;
import io.aelf.utils.AElfException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IContractBehaviour {
    /**
     * Call a VIEW/SEND method in the particular contract
     * on the blockchain network, and get the result.
     * Actually, when you are trying to execute a transaction, in fact, you
     * are calling the AElf.ContractNames.Token contract's transfer method
     * to execute this token transaction (ELF token or others).
     * Things go on a similar way when you are trying to call a contract's
     * method, whether it is a VIEW method or a SEND method.
     * When calling this method, a {@link TransactionDto} object will be sent
     * to the peer, it contains information that will be used when calling
     * a contract method.
     *
     * @param contractName   name of the contract that contains target method
     * @param methodName     method name that is about to call
     * @param privateKey     since calling a contract method is actually
     *                       sending a transaction to the blockchain system,
     *                       privateKey is needed for sign
     * @param isViewMethod   whether this method is a VIEW method, calling VIEW
     *                       methods will have no consequences and side effects,
     *                       they will only provide a result and have no effect
     *                       on the contract's State.The other side, SEND methods,
     *                       will change its contract's State.
     * @param optionalParams params that need to be sent to
     * @return {@link String} method result
     * @throws AElfException when issues happen
     */
    String callContractMethod(@Nonnull String contractName,
                              @Nonnull String methodName, @Nonnull String privateKey,
                              boolean isViewMethod, @Nullable String optionalParams) throws AElfException;

    String callContractMethod(@Nonnull String contractName,
                              @Nonnull String methodName, @Nonnull String privateKey,
                              boolean isViewMethod, @NotNull JsonObject optionalParams) throws AElfException;

    String callContractMethod(@Nonnull String contractName, @Nonnull String methodName,
                              @Nonnull String privateKey, boolean isViewMethod) throws AElfException;
}
