package io.aelf.contract;

import io.aelf.contract.instance.*;

public class GlobalContract {
    public static final TokenContract tokenContract = new TokenContract();
    public static final TokenConverterContract tokenConverterContract = new TokenConverterContract();
    public static final GenesisContract genesisContract = new GenesisContract();
    public static final ConsensusContract consensusContract = new ConsensusContract();
    public static final VoteContract voteContract = new VoteContract();
}
