export type PollStatus = 'OPEN' | 'CLOSED' | 'CANCELLED';

export interface OptionSummary {
  id: number;
  text: string;
}

export interface Poll {
  id: number;
  title: string;
  question: string;
  status: PollStatus;
  createdAt: string;
  closedAt: string | null;
  ownerId: number;
  ownerName: string;
  options: OptionSummary[];
}

export interface OptionResult {
  optionId: number;
  text: string;
  voteCount: number;
}

export interface PollResults {
  pollId: number;
  title: string;
  question: string;
  status: PollStatus;
  options: OptionResult[];
  userVotedOptionId: number | null;
}
