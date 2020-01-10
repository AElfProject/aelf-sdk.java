package io.aelf.schemas;


public class RoundDto {
    private long RoundNumber;
    private long TermNumber;
    private long RoundId;
    private MinerInRoundDto ExtraBlockProducerOfPreviousRound;
    private long ConfirmedIrreversibleBlockRoundNumber;
    private long ConfirmedIrreversibleBlockHeight;
    private boolean IsMinerListJustChanged;
}
