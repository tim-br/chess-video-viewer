def all_videos_for_semester(level, semester)
  if level == 1 && semester == 1
    Video.where(is_beginner: true).order("week_number")
  elsif level == 2 && semester ==1
    Video.where(is_advanced: true).order("week_number")
  end
end
