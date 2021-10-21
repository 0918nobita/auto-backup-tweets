# auto-backup-tweets

An open source software that retrieves and backs up user’s tweet history to their own local storage automatically

## Motivation

The reason I'm creating this software is to allow individual twitter users to keep their tweets at hand at their own will, in case they are affected by network failures or accidentally suspended.

## Development

### Requirements

- Clojure CLI
- direnv

### Setup

``.envrc`` :

```bash
export TWITTER_CONSUMER_KEY="..."
export TWITTER_CONSUMER_SECRET="..."
```

### Execute program

```bash
direnv allow
clj -M:main
```

### Run tests

```bash
clj -M:test
```
