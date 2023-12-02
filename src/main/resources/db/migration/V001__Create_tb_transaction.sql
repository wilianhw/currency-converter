CREATE TABLE "transaction" (
	id bigserial NOT NULL,
	purchase_amount numeric(38, 2) NOT NULL,
	transaction_date date NOT NULL,
	description varchar(50) NOT NULL,

	CONSTRAINT transaction_pkey PRIMARY KEY(id)
);