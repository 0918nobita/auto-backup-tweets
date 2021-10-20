# auto-backup-tweets

``.envrc``:

```bash
export TWITTER_CONSUMER_KEY="..."
export TWITTER_CONSUMER_SECRET="..."
```

```bash
direnv allow
clj -M -m auto-backup-tweets.core
```
